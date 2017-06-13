import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
public class Test {
	public static void main(String[] args) {
		if ( StringUtils.isNotBlank("1")&&StringUtils.isNotBlank("2") ){
			System.out.println("sss");
		}else if ( StringUtils.isNotBlank("1") ){
			System.out.println("sssssss");
		}
	}
}
