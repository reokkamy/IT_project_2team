package webproject_2team.lunch_matching.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
// MERG TEST
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private String writer;

    private String region;

    private String genderLimit; // 남, 여, 성별상관무

    private String foodCategory; // 한식, 중식 등

    private String imagePath; // 이미지 경로

    private LocalDateTime createdAt;

    private Integer deadlineHours; // 마감시간 (시간 단위: 1, 2, 3, 24)

    private LocalDateTime deadlineAt; // 실제 마감시간

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    // 마감 여부 확인 메서드
    public boolean isExpired() {
        if (deadlineAt == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(deadlineAt);
    }

    // 남은 시간 계산 (분 단위)
    public long getRemainingMinutes() {
        if (deadlineAt == null || isExpired()) {
            return 0;
        }
        return java.time.Duration.between(LocalDateTime.now(), deadlineAt).toMinutes();
    }
}