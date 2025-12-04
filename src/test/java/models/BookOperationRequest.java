package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BookOperationRequest {
    private String userId;
    private CollectionOfIsbn collectionOfIsbns;

    public BookOperationRequest(String userId, String isbn) {
        this.userId = userId;
        this.collectionOfIsbns = new CollectionOfIsbn(new Isbn[]{new Isbn(isbn)});
    }

    @Data
    public static class CollectionOfIsbn {
        private Isbn[] isbns;

        public CollectionOfIsbn(Isbn[] isbns) {
            this.isbns = isbns;
        }
    }

    @Data
    public static class Isbn {
        private String isbn;

        public Isbn(String isbn) {
            this.isbn = isbn;
        }
    }
}
