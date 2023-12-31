import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 처음에 2차원 배열로 dp만들었으나 n*n*4 = 9*10^12으로 메모리초과 => dp 무조건 1차원으로 만들어야!!
 * dp[j] : 현재날까지의 상담 종료일이 j일 때 최대 이익
 * 
 * 또한 n*n = 9*10^12은 시간초과이므로 모든 배열은 탐색하면 x 
 * => 문제에서 주어진 Ti<=50이므로 이전 50개까지만 탐색!! n*50= 125*10^6이므로 가능!!
 */
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine()); // 퇴사까지의 날짜
		int[] dp = new int[n+2];
		
		// 첫번째 날
		StringTokenizer st = new StringTokenizer(br.readLine());
		int time = Integer.parseInt(st.nextToken()); // 상담 기간
		int money = Integer.parseInt(st.nextToken()); // 받는 금액
		if(1+time <= n+1) {
			dp[1+time] = money;
		}
		
		// 두번째 날 ~
		for(int i=2; i<=n; i++) {
			st = new StringTokenizer(br.readLine());
			time = Integer.parseInt(st.nextToken()); // 상담 기간
			money = Integer.parseInt(st.nextToken()); // 받는 금액

			// 현재 dp에는 이전 날짜까지의 상담 종료일이 j일때의 최대 이익 정보가 저장되어있음
			
			// 상담 끝났을 때(상담 종료일이 현재 날짜 50일이전까지)의 경우를 고려
			int start = 1;
			if(i-50 > 1) start = i-50;
			for(int j=start; j<=i; j++) {
				// 현재날짜에 새로운 상담 시작 x => 상담 종료일 그대로 유지
				// 현재날짜에 새로운 상담 시작 o(상담을 시작해도 퇴사일 전에 끝남) => 상담 종료일 업데이트
				if(i+time <= n+1) {
					dp[i+time] = Math.max(dp[i+time], dp[j] + money);			
				} 
			}
			
			// 현재 날짜에 무조건 상담 시작했을 때 (상담을 시작해도 퇴사일 전에 끝남)의 경우를 고려
			if(i+time <=n+1) {
				dp[i+time] = Math.max(dp[i+time], money);
			}
		}
		
		int max = 0;
		for(int i=1; i<=n+1; i++) {
			max = Math.max(max, dp[i]);
		}
		
		System.out.println(max);
	}
}
