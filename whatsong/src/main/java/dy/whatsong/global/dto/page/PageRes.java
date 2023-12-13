package dy.whatsong.global.dto.page;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageRes<T>(PageInfo pageInfo, List<T> pageData) {

    public PageRes(Page<T> page) {
        this(new PageInfo(
                        page.getNumber() + 1,
                        page.getSize(),
                        page.getTotalPages(),
                        page.getTotalElements(),
                        page.isFirst(),
                        page.isLast()
                ),
                page.getContent()
        );
    }

    public record PageInfo(int page, int size, int totalPage, long totalSize, boolean isFirst, boolean isLast) {
    }


}
