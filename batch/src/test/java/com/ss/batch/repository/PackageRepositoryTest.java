package com.ss.batch.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.ss.batch.entity.PackageEntity;

@SpringBootTest
public class PackageRepositoryTest {

	@Autowired
	private PackageRepository repo;

//	@Test
//	public void test_save() {
//
//		// given(테스트를 위한 초기 데이터 설정)
//		PackageEntity entity = new PackageEntity();
//		entity.setPackageName("바디 챌린지 pt 12주");
//		entity.setPeriod(84);
//
//		// when(실제로 테스트할 작업 수행(데이터베이스 저장)
//		repo.save(entity);
//
//		// then(예상 결과를 검증(ID가 자동으로 생성)
//		// 아이디가 자동으로 들어가면 null이 아닌걸 확인
//		assertNotNull(entity.getPackSeq());
//	}

	@Test
	public void test_findByCreateAtAfter() {
		// given
		// 현재시간에서 1분전
		LocalDateTime dateTime = LocalDateTime.now().minusMinutes(1);
		PackageEntity pack1 = new PackageEntity();
		pack1.setPackageName("학생 전용 3개월");
		pack1.setPeriod(90);
		repo.save(pack1);

		PackageEntity pack2 = new PackageEntity();
		pack1.setPackageName("학생 전용 6개월");
		pack1.setPeriod(90);
		repo.save(pack2);

		// when(특정 시간 이후에 생성된 패키지를 페이징 및 정렬 조건에 맞춰서 조회
		// 최신패키지를 조회(내림차순)
		PageRequest page = PageRequest.of(0, 1, Sort.by("pack_seq").descending());
		List<PackageEntity> result = repo.findByCreateAtAfter(dateTime, page);

		// then: 결과 검증
		System.out.println("result: " + result);
		assertEquals(1, result.size());

		assertEquals(pack2.getPackSeq(), result.get(0).getPackSeq());
	}

	@Test
	public void test_updateCountAndPeriod() {

		// given
		// 새로운 패키지 생성 바디 프로필 이벤트 4개월
		// 데이터베이스 저장

		PackageEntity pack1 = new PackageEntity();
		pack1.setPackageName("바디 프로필 이벤트 4개월");
		pack1.setPeriod(90);
		repo.save(pack1);

		// when 업데이트된 데이터를 다시 조회
		// 업데이트를 할 때 updateCountAndPeriod
		pack1.setCount(30);
		pack1.setPeriod(120);
		repo.save(pack1);

		System.out.println(pack1.getPackSeq());
		final PackageEntity update = repo.findById(pack1.getPackSeq()).get();
		System.out.println(update.toString());
		// then 업데이트된 값이 올바르게 저장되었는지 검증
		assertEquals(30, update.getCount());
		assertEquals(120, update.getPeriod());
	}
	
	
}
