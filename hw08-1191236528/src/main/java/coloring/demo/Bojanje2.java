package coloring.demo;

import java.util.Arrays;

import coloring.algorithms.*;
import marcupic.opjj.statespace.coloring.*;

/**
 * Class with which we start {@link FillApp}
 * @author Lucija Valentić
 *
 */
public class Bojanje2 {

	/**
	 * Main method that is called in the beginning of a program
	 * @param args arguments of a command line
	 */
	public static void main(String[] args) {
		
		FillApp.run(FillApp.OWL, Arrays.asList(bfs,dfs,bfsv));;
		
	}
	
	/**
	 * Class that represents algorithm BFS, it is
	 * used in {@link FillApp} for coloring the picture
	 */
	private static final FillAlgorithm bfs = new FillAlgorithm() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfs(col, col, col, col);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getAlgorithmTitle() {
			return "Moj bfs!"; 
		}
		
		
	};
	

	/**
	 * Class that represents algorithm DFS, it is
	 * used in {@link FillApp} for coloring the picture
	 */
	private static final FillAlgorithm dfs = new FillAlgorithm() {

		/**
		 *  {@inheritDoc}
		 */
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.dfs(col, col, col, col);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getAlgorithmTitle() {
			return "Moj dfs!"; 
		}
		
		
	};
	

	/**
	 * Class that represents algorithm BFSV, it is
	 * used in {@link FillApp} for coloring the picture
	 */
	private static final FillAlgorithm bfsv = new FillAlgorithm() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfsv(col, col, col, col);			
			
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getAlgorithmTitle() {
			return "Moj bfsv!"; 
		}
		
		
	};

}
