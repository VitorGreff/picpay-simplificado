package greff.picpay.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @AllArgsConstructor
public class JSONException implements Serializable {
    private String errorType;
    private String message;
}
