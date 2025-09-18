package org.example.crm_back.dto.order;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderFormDataDto {
    @Size(max = 25, message = "Name: 25 symbols max")
    private String name;
    @Size(max = 25, message = "Surname: 25 symbols max")
    private String surname;
    @Pattern(regexp = "^(?=.{2,100}$)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email: invalid format")
    private String email;
    @Size(max=12, message = "Phone: 12 symbols max")
    private String phone;

    private String age;
    @Size(max = 10, message = "Course: 10 symbols max")
    private String status;
    @Size(max = 15, message = "Course: 15 symbols max")
    private String course;
    @Size(max = 15, message = "Course format: 15 symbols max")
    private String courseFormat;
    @Size(max = 100, message = "Course type: 100 symbols max")
    private String courseType;

    private Double sum;

    private Double alreadyPaid;
    @Size(max = 50, message = "Group name: 50 symbols max")
    private String groupName;
}
