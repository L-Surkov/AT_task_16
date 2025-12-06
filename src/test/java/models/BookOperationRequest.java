package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BookOperationRequest {
    private String userId;
    private Isbn[] collectionOfIsbns;

    @JsonIgnore
    private String isbn;

    public BookOperationRequest(String userId, String isbn) {
        this.userId = userId;
        this.collectionOfIsbns = new Isbn[]{new Isbn(isbn)};
        this.isbn = isbn;
    }

    public String toDeleteJson() {
        return String.format("{\"userId\":\"%s\",\"isbn\":\"%s\"}", userId, isbn);
    }

    @Data
    public static class Isbn {
        private String isbn;
        public Isbn(String isbn) { this.isbn = isbn; }
    }
}
