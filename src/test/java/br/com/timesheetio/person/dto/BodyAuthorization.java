package br.com.timesheetio.person.dto;

import java.io.Serializable;

import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BodyAuthorization extends  ResourceOwnerPasswordResourceDetails implements Serializable {

	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;
	
	private String grant_type;
}
