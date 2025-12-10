package models.booksmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookResponseModel {
    String isbn,
            title,
            subTitle,
            author,
            publisher,
            pages,
            description,
            website;
    @JsonProperty("publish_date")
    String publishDate;
}