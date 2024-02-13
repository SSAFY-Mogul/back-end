package com.mogul.demo.user.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserCheckEmailRequest {
	@NotBlank
	@Email
	@Length(min = 1, max = 320)
	@Schema(example = "test@test.test")
	private String email;
}
