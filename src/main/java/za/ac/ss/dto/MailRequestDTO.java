package za.ac.ss.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailRequestDTO implements Serializable {
	private static final long serialVersionUID = -4896192806251691218L;
	private String to;
	private String from;
	private String subject;
	private String body;
	private byte[] attachment;
}
