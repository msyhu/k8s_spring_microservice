package vmware.services.organization.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

import java.util.*;
import java.lang.management.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.management.*;

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

    @GetMapping("/example")
    public void example() throws Exception{

        javassist.ClassPool cp = javassist.ClassPool.getDefault();
        for (int i = 0; ; i++) {
            if (i % 1000 == 0) Thread.sleep(100);
            Class c = cp.makeClass("io.github.yaboong.Generated" + i).toClass();
        }
    }

    @GetMapping("/systemgc")
    public void systemgc() {

        System.gc();
    }

    @GetMapping("/memoryleak/{loop}")
    public void memoryleak(@PathVariable("loop") String loopStr) throws Exception {

        int loop = Integer.parseInt(loopStr);
//        (new MeinBoesesMemoryLeak()).run(loop);
    }

    @GetMapping("/threadsleep/{loop}/{time}")
    public void threadsleep(@PathVariable("loop") String loopStr, @PathVariable("time") String timeStr) throws Exception {
        LOGGER.info("threadsleep start");

        int loop = Integer.parseInt(loopStr);
        int time = Integer.parseInt(timeStr);
        List<Integer> li = IntStream.range(1, 100000).boxed().collect(Collectors.toList());
//        for (int i=1; i < loop; i++) {
//            if (i % 100 == 0) {
        Thread.sleep(time);
//            }
        IntStream.range(0, 100000).forEach(li::add);
//        }
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

class MeinBoesesMemoryLeak
{
    long startUptime  = -1;
    long startCpuTime = -1;

    // Memory-Leak-Schleife:
    public void run(int loop) throws Exception
    {
        Thread.sleep( 5000 );
        String       meinString = "";
        List<String> meineListe = new ArrayList<String>();
        int cnt = 0;
        while( cnt < loop ) {
            cnt++;
            for( int i=0; i<1000; i++ ) {
                meinString += i;
            }
            meineListe.add( meinString );
            long meinVerbrauchterSpeicher = 0;
            for( String str : meineListe ) {
                meinVerbrauchterSpeicher += str.length();
            }
            System.out.println("Verbrauchter Speicher: " + (meinVerbrauchterSpeicher / 1024) +
                    " KByte; Laenge letzter String / 1000: " + (meinString.length() / 1000) );
            showGarbageCollection();
            showCpuPercent();
        }
    }

    // Anzeige der GarbageCollection:
    void showGarbageCollection() throws Exception
    {
        List<GarbageCollectorMXBean> gcList = ManagementFactory.getGarbageCollectorMXBeans();
        for( Iterator<GarbageCollectorMXBean> iterator = gcList.iterator(); iterator.hasNext(); ) {
            GarbageCollectorMXBean gc = iterator.next();
            System.out.println( "GarbageCollection: Count=" + gc.getCollectionCount() +
                    ", Time=" + gc.getCollectionTime() + " (" + gc.getName() + ")" );
        }
    }

    // Anzeige der Auslastung der CPUs:
    void showCpuPercent() throws Exception
    {
        RuntimeMXBean         rt = ManagementFactory.getRuntimeMXBean();
        OperatingSystemMXBean op = ManagementFactory.getOperatingSystemMXBean();
        MBeanServer           ms = ManagementFactory.getPlatformMBeanServer();
        Long cpuTime = (Long) ms.getAttribute( new ObjectName( "java.lang:type=OperatingSystem" ), "ProcessCpuTime" );
        if( startUptime == -1 && startCpuTime == -1 ) {
            startUptime  = rt.getUptime();
            startCpuTime = cpuTime.longValue();
        } else {
            long cpuPercent = (cpuTime.longValue() - startCpuTime) /
                    ((rt.getUptime() - startUptime) * op.getAvailableProcessors() * 10000);
            System.out.println( "CPU-Anzahl=" + op.getAvailableProcessors() +
                    ", CPU-Last-Prozent=" + cpuPercent + "%" );
        }
    }
}