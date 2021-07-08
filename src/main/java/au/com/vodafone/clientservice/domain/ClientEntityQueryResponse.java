package au.com.vodafone.clientservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntityQueryResponse {
    private Long id;

    private String name;

    private String email;
}
