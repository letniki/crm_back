package org.example.crm_back.services;

import lombok.RequiredArgsConstructor;
import org.example.crm_back.entities.Group;
import org.example.crm_back.repositories.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    @Transactional
    public Group getOrCreateGroup(String groupName) {
        return groupRepository.findByName(groupName)
                .orElseGet(() -> groupRepository.save(new Group(groupName)));
    }
    public List<String> getAllGroupNames() {
        return groupRepository.findAll().stream()
                .map(Group::getName)
                .toList();
    }
}
