package org.example.exceptionhandlerexample.reuqest.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class UserRequest {
    @NotBlank(message = "姓名不可为空")
    @Length(min = 6, max = 10, message = "姓名长度范围 6-10")
    private String name;

    @NotNull(message = "年龄不可为空")
    @Range(min = 0, max = 150, message = "年龄范围 0-150")
    private Integer age;
}
