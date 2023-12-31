import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int n; // 지도의 크기
	static int[][] map; // 2차원 지도를 저장
	static int[][] group; // 각 좌표에 해당하는 그룹번호를 저장
	
	// 방향벡터(상하좌우)
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		n = Integer.parseInt(br.readLine());
		
		// 2차원 지도 생성 및 입력받기
		map = new int[n][n]; // x좌표, y좌표, 그룹번호
		for(int r=0; r<n; r++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int c=0; c<n; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 초기 1(육지)인 애들의 그룹 번호 지정하기
		group = new int[n][n];
		Queue<int[]> queue = new ArrayDeque<>(); // 그룹 번호 지정을 위한 큐
		Queue<int[]> start = new ArrayDeque<>(); // 현재 1(육지)들의 좌표 저장
		int idx = 1; // 현재 그룹 번호
		for(int r=0; r<n; r++) {
			for(int c=0; c<n; c++) {
				// 2차원 지도에서 1(육지)이고, 그룹번호를 아직 지정하지 않았으면 => bfs 수행
				if(map[r][c] == 1 && group[r][c] == 0) {
					// 큐에 넣고
					queue.offer(new int[] {r,c,1}); // r:x좌표, c:y좌표, 1:이동거리
					start.offer(new int[] {r,c,1}); // r:x좌표, c:y좌표, 1:이동거리
					group[r][c] = idx; // 현재 그룹번호를 지정
					// 큐가 빌때까지 반복
					while(!queue.isEmpty()) {
						int[] cur = queue.poll(); // 큐에서 1개 꺼내서
						for(int i=0; i<4; i++) { // 상하좌우 4방향 탐색
							int x = cur[0] + dx[i];
							int y = cur[1] + dy[i];
							// 배열의 인덱스 넘지 않고
							if(x>=0 && x<n && y>=0 && y<n) {
								// 2차원 지도에서 1(육지)이고, 현재 방문한 적이 없다면(group[x][y] == 0)
								if(map[x][y] == 1 && group[x][y] == 0) {
									// 큐에 넣어줌
									queue.offer(new int[] {x,y,1});
									start.offer(new int[] {x,y,1});
									group[x][y] = idx; // 현재 그룹번호를 지정
								}
							}
						}
					}
					idx++; // 그룹 번호 1 증가
				}
			}
		}
		
		// bfs를 수행하며 이동거리 상하좌우 1씩 이동하며 늘림(예전 숨바꼭질 문제 같은 느낌~)
		int result = Integer.MAX_VALUE; // 결과값
		// 큐가 빌때까지 반복
		while(!start.isEmpty()) {
			int[] cur = start.poll(); // 큐에서 1개 꺼내서
			for(int i=0; i<4; i++) { // 상하좌우 4방향 탐색
				int x = cur[0] + dx[i];
				int y = cur[1] + dy[i];
				// 배열의 인덱스 넘지 않고
				if(x>=0 && x<n && y>=0 && y<n) {
					// 현재 방문한 적이 없다면
					if(map[x][y] == 0) {
						map[x][y] = cur[2]+1; // 현재 이동거리+1로 거리를 저장하고
						start.offer(new int[] {x,y,cur[2]+1}); // 큐에 다시 넣고
						group[x][y] = group[cur[0]][cur[1]]; // 현재 그룹번호를 지정
					}
					// 다른 섬에 방문했다면(짝수개의 다리 사용)
					else if(map[x][y] == cur[2] && group[x][y] != group[cur[0]][cur[1]]){
						int min = 2*((cur[2]+1)-2); // 임시 최솟값
						if(result > min) result = min; // 더 작다면 => 업데이트
						break;
					}
					// 다른 섬에 방문했다면(홀수개의 다리 사용)
					else if(map[x][y] == cur[2]+1 && group[x][y] != group[cur[0]][cur[1]]) {
						int min = 2*((cur[2]+1)-2)+1; // 임시 최솟값							
						if(result > min) result = min; // 더 작다면 => 업데이트
						break;
					}
				}
			}
		}

		System.out.println(result);
	}
}
