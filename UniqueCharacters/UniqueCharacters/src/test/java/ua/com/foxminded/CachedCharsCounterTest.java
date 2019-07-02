package ua.com.foxminded;

import static org.mockito.Mockito.*;

import java.util.stream.IntStream;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CachedCharsCounterTest {

	@Mock
	private UniqueCharsCounter uniqueCharsCounter;

	@InjectMocks
	private CachedCharsCounter cachedCharsCounter;

	@Test
	public void givenText_whenCountChars_thenDelegated() {
		
		cachedCharsCounter.countChars("Abbccc");
		
		verify(uniqueCharsCounter, times(1)).countChars("Abbccc");
	}

	@Test
	public void givenText_whenCountCharsCalledTwice_thenDelegatedOnce() {
		
		IntStream.range(0, 2).forEach(i -> cachedCharsCounter.countChars("Aaabbc"));
		
		verify(uniqueCharsCounter, times(1)).countChars("Aaabbc");
	}
}