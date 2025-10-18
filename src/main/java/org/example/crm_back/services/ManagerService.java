package org.example.crm_back.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.crm_back.dto.manager.CreateManagerRequestDto;
import org.example.crm_back.dto.manager.ManagerDto;
import org.example.crm_back.dto.order.StatDto;
import org.example.crm_back.dto.pagination.PaginationResponseDto;
import org.example.crm_back.entities.Manager;
import org.example.crm_back.enums.Role;
import org.example.crm_back.mappers.ManagerMapper;
import org.example.crm_back.repositories.ManagerRepository;
import org.example.crm_back.repositories.OrderRepository;
import org.example.crm_back.utilities.JwtUtility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService implements UserDetailsService {
    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;
    private final JwtUtility jwtUtility;
    private final OrderRepository orderRepository;

    public PaginationResponseDto<ManagerDto> getManagers(Integer page) {
        Pageable pageable = PageRequest.of(
                page - 1,
                3,
                Sort.by("id")
                        .descending());
        Page<Manager> managersPage = managerRepository.findByRoleNot(Role.ROLE_ADMIN, pageable);
        List<ManagerDto> managerDtos = managersPage.getContent()
                .stream()
                .map(manager -> {
                    List<StatDto> stats = orderRepository.findAllByManager(manager.getSurname())
                            .stream()
                            .collect(Collectors.groupingBy(
                                    order -> order.getStatus() == null ? "Not assigned" : order.getStatus(),
                                    Collectors.counting()
                            ))
                            .entrySet()
                            .stream()
                            .map(entry -> new StatDto(entry.getKey(), entry.getValue()))
                            .toList();
                    return managerMapper.toDto(manager, stats);
                })
                .toList();

        Integer nextPage = managersPage.hasNext() ? page + 1 : null;
        Integer prevPage = managersPage.hasPrevious() ? page - 1 : null;

        return new PaginationResponseDto<>(
                managersPage.getTotalElements(),
                pageable.getPageSize(),
                nextPage,
                prevPage,
                managerDtos
        );
    }

    @Transactional
    public void createManager(CreateManagerRequestDto dto, String token) {
        Manager admin = managerRepository.findByEmail(jwtUtility.extractUsername(token))
                .orElseThrow(() -> new RuntimeException("Invalid role, unable to create a manager"));

        if (admin.getRole() == Role.ROLE_ADMIN) {
            Manager manager = new Manager();
            manager.setEmail(dto.getEmail());
            manager.setPassword(null);
            manager.setRole(Role.ROLE_MANAGER);
            manager.setName(dto.getName());
            manager.setSurname(dto.getSurname());
            manager.setIsActive(false);
            manager.setIsBanned(false);
            manager.setLastLogIn(null);
            managerRepository.save(manager);
        }
    }

    public void toggleBan(Long id) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        manager.setIsBanned(!manager.getIsBanned());
        managerRepository.save(manager);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return managerRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with provided email was not found"));
    }
}
