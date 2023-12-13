package dy.whatsong.global.dto.page;

import javax.validation.constraints.Positive;

public record PageReq(@Positive int page, @Positive int size) {

}
