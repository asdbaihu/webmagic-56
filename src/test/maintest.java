package test;


import com.wyy.spider.common.utils.DateUtil;

public class maintest {

	public static void main(String[] args) {
		String localTime = DateUtil.utc2LocalTime("2017-01-01T13:10:00", "yyyy-MM-dd'T'HH:mm:ss", "yyyyMMddHHmm");
		System.out.println(localTime);
	}
}
