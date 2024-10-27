package io.github.yajuhua.podcast2API.extension.build;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtendList {
    private List<Input> inputList;
    private List<Select> selectList;
}
