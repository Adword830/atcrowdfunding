import com.houpu.crowd.util.CrowdUtil;
import org.junit.Test;

public class UtilTest {
    @Test
    public void md5Test(){
        String md5 = CrowdUtil.md5("123456");
        // e10adc3949ba59abbe56e057f20f883e
        // e10adc3949ba59abbe56e057f20f883e
        System.out.println(md5);
    }
}
