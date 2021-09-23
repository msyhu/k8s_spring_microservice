package vmware.services.organization.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    public long getHeapSize() {
        return Runtime.getRuntime().totalMemory();
    }

    @GetMapping("/insertstring/{loopcount}")
    public String insertString(@PathVariable("loopcount") String loopStr) {

        LOGGER.info("insertstring loop: " + loopStr + "\n");
        LOGGER.info("Heap Size(M) : " + getHeapSize() + " MB");

        int loop = Integer.parseInt(loopStr);
        Leak lk = new Leak();
        for(int i = 0 ; i < loop; i++){
            lk.addList(i);
            lk.removeStr(i);
        }

        return "ok";
    }

    @GetMapping("/makeintegerarray/{loop}/{size}")
    public String makeIntegerArray(@PathVariable("loop") String loopStr, @PathVariable("size") String sizeStr) {

        LOGGER.info("makeIntegerArray loop : " + loopStr + " size : " + sizeStr + "\n");
        LOGGER.info("Heap Size(M) : " + getHeapSize() + " MB");

        int size = Integer.parseInt(sizeStr);
        int loop = Integer.parseInt(loopStr);
        List<Integer[]> integerArrList = new ArrayList<>();
        for(int i = 0 ; i < loop; i++) {
            integerArrList.add(new Integer[size * size * size]);
        }

        return "ok";
    }
}

class Leak {
    private ArrayList<String> lst = new ArrayList<>();

    public void addList(int i) {
        lst.add("abcdefghij" + i);
    }

    public void removeStr(int i) {
        Object obj = lst.get(i);
        obj = null;
    }

}