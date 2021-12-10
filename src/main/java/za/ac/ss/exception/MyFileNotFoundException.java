package za.ac.ss.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MyFileNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;
}
