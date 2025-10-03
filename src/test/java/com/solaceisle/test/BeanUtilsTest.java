package com.solaceisle.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Source{

    private InnerSource inner;

    private List<InnerSource> data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InnerSource{
        private String name;
        private String age;
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Target{

    private InnerTarget inner;

    private List<InnerTarget> data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InnerTarget{
        private String name;
    }
}

public class BeanUtilsTest {

    @Test
    public void testCopyProperties() {
        var source = new Source();
        source.setInner(new Source.InnerSource("11","22"));
        source.setData(List.of(new Source.InnerSource("1","2")));
        var target = new Target();
        BeanUtils.copyProperties(source, target);
        System.out.println("target = " + target);
        // 结果为 null
    }
}
