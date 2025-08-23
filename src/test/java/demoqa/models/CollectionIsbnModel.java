package demoqa.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CollectionIsbnModel {
    String userId;
    List<IsbnModel> collectionOfIsbns;

}
