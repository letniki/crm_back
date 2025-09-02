package org.example.crm_back.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterDto {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String course;
    private String courseFormat;
    private String courseType;
    private String status;
    private String groupName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public boolean isEmpty() {
        return (name == null || name.isEmpty()) &&
                (surname == null || surname.isEmpty()) &&
                (email == null || email.isEmpty()) &&
                (phone == null || phone.isEmpty()) &&
                (course == null || course.isEmpty()) &&
                (courseFormat == null || courseFormat.isEmpty()) &&
                (courseType == null || courseType.isEmpty()) &&
                (status == null || status.isEmpty()) &&
                (groupName == null || groupName.isEmpty()) &&
                (startDate == null) &&
                (endDate == null);
    }

}
