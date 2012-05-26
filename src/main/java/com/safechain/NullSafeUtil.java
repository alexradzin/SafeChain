package com.safechain;

import com.safechain.annotation.SafeChainWrapper;

/**
 * Together with {@link NullSafeChain} this class is the entry point to library. 
 * This class contains a set of static utilities that simplifies their usage.
 * Actually the static utilities just wrap respective methods of class  
 * The author recommends to use {@code import static} statement for all needed methods
 * e.g. {@code import static com.safechain.nullsafe} so that the methods usage 
 * will be very simplified, e.g. {@link nullsafe(person)}. 
 * <br/><br/>
 * Please see explanations written in class {@link NullSafeChain}: they are relevant here also.
 * <br/><br/>
 * This class contains fully named methods and their short aliases. For example object may 
 * be wrapped using method {@link #nullsafe(Object)} as well as method {@link #$(Object).
 * Method {@link #$arr(int[], int)} is alias of method {@link #nullsafeArray(int[], int)} <i>etc</i>.
 * <br/><br/>
 * Author knows that using character {@code $} as a part of identifier name does not follow 
 * widely used Java language naming convention and is not self-explained. He understands
 * that many people will dislike this, so he provided 2 versions: full named and short named.
 * Author thinks that when reader knows the context code {@code $(person).getSpouse().getFirstName()}
 * is not less and even probably more readable than code {@code nullsafe(person).getSpouse().getFirstName()}.
 * Anyway the user is welcome to choose the method variation he likes.
 * 
 * <br/><br/>
 * 
 * Here is the code example that shows how to use this utility:
 * <pre><code>
 * import static com.safechain.NullSafeUtil.$;
 * ...............
 * 
 * Person person = new Person();
 * ...............
 * //The following line does not cause NPE even for single person
 * String spouseName = $(person).getSpouse().getFirstName();
 * // This line return null even if person does not have children. No NoSuchElementException.
 * Integer olderChildAge = $(person).getChildren().iterator().next().getAge();
 * </code></pre>  
 * @author alexr
 */
@SafeChainWrapper
public class NullSafeUtil {
	private final static NullSafeChain chain = new NullSafeChain();
	
	public static <T> T nullsafe(T instance) {
		return chain.nullsafe(instance);
	}
	
	public static <T> T nullsafeArray(T[] arr, int i) {
		return chain.nullsafeArray(arr, i);
	}

	public static <T> T nullsafeArray(T[][] arr, int i, int j) {
		return chain.nullsafeArray(arr, i, j);
	}

	public static <T> T nullsafeArray(T[][][] arr, int i, int j, int k) {
		return chain.nullsafeArray(arr, i, j, k);
	}

	public static <T> T nullsafeArray(Object arr, Class<T> clazz, int ... i) {
		return chain.nullsafeArray(arr, clazz, i);
	}

	
	// aliases: aliase$ :)
	public static <T> T $(T instance) {
		return nullsafe(instance);
	}
	
	public static <T> T $arr(T[] arr, int i) {
		return nullsafeArray(arr, i);
	}

	public static <T> T $arr(T[][] arr, int i, int j) {
		return nullsafeArray(arr, i, j);
	}

	public static <T> T $arr(T[][][] arr, int i, int j, int k) {
		return nullsafeArray(arr, i, j, k);
	}

	public static <T> T $arr(Object arr, Class<T> clazz, int ... i) {
		return nullsafeArray(arr, clazz, i);
	}

	
	// array access methods for primitive types
	public static boolean nullsafeArray(boolean[] arr, int i) {
		return chain.nullsafeArray(arr, i);
	}

	public static boolean nullsafeArray(boolean[][] arr, int i, int j) {
		return chain.nullsafeArray(arr, i, j);
	}

	public static boolean nullsafeArray(boolean[][][] arr, int i, int j, int k) {
		return chain.nullsafeArray(arr, i, j, k);
	}
	
	public static boolean $arr(boolean[] arr, int i) {
		return nullsafeArray(arr, i);
	}

	public static boolean $arr(boolean[][] arr, int i, int j) {
		return nullsafeArray(arr, i, j);
	}

	public static boolean $arr(boolean[][][] arr, int i, int j, int k) {
		return nullsafeArray(arr, i, j, k);
	}
	

	public static short nullsafeArray(short[] arr, int i) {
		return chain.nullsafeArray(arr, i);
	}

	public static short nullsafeArray(short[][] arr, int i, int j) {
		return chain.nullsafeArray(arr, i, j);
	}

	public static short nullsafeArray(short[][][] arr, int i, int j, int k) {
		return chain.nullsafeArray(arr, i, j, k);
	}
	
	public static short $arr(short[] arr, int i) {
		return nullsafeArray(arr, i);
	}

	public static short $arr(short[][] arr, int i, int j) {
		return nullsafeArray(arr, i, j);
	}

	public static short $arr(short[][][] arr, int i, int j, int k) {
		return nullsafeArray(arr, i, j, k);
	}

	
	
	public static int nullsafeArray(int[] arr, int i) {
		return chain.nullsafeArray(arr, i);
	}

	public static int nullsafeArray(int[][] arr, int i, int j) {
		return chain.nullsafeArray(arr, i, j);
	}

	public static int nullsafeArray(int[][][] arr, int i, int j, int k) {
		return chain.nullsafeArray(arr, i, j, k);
	}
	
	public static int $arr(int[] arr, int i) {
		return nullsafeArray(arr, i);
	}

	public static int $arr(int[][] arr, int i, int j) {
		return nullsafeArray(arr, i, j);
	}

	public static int $arr(int[][][] arr, int i, int j, int k) {
		return nullsafeArray(arr, i, j, k);
	}
	
	public static long nullsafeArray(long[] arr, int i) {
		return chain.nullsafeArray(arr, i);
	}

	public static long nullsafeArray(long[][] arr, int i, int j) {
		return chain.nullsafeArray(arr, i, j);
	}

	public static long nullsafeArray(long[][][] arr, int i, int j, int k) {
		return chain.nullsafeArray(arr, i, j, k);
	}
	
	public static long $arr(long[] arr, int i) {
		return nullsafeArray(arr, i);
	}

	public static long $arr(long[][] arr, int i, int j) {
		return nullsafeArray(arr, i, j);
	}

	public static long $arr(long[][][] arr, int i, int j, int k) {
		return nullsafeArray(arr, i, j, k);
	}
	
	
	/**
	 * Package protected getter of static instance of chain.
	 * Used mostly for tests or for advanced configuration of chain when being used via this static utility.
	 * @return
	 */
	static NullSafeChain getChain() {
		return chain;
	}
}
