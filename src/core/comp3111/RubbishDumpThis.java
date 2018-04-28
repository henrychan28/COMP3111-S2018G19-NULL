package core.comp3111;

public class RubbishDumpThis {
	
	private static class Haha{
		private int i;
		public Haha(int i) {
			Seti(i);
		}
		public void Seti(int i) {
			this.i=i;
		}
	}
	public static void main(String[] args) {
		Haha i = new Haha(2);
		System.out.println(i);
		Treatment(i);
		System.out.println(i);

	}
	
	private static void Treatment(Haha a) {
		a = null;
		System.out.println(a);
	}

}
