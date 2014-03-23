import java.util.Arrays;

public class Splines extends Formula
   {

   private Cubic[] splines;
   private double[] limits;

   public Splines( Double[] x, Double[] y )
      {
      super();
      limits = new double[ x.length ];
      for( int i = 0; i < x.length; i++ )
         limits[i] = x[i].doubleValue();
      //Arrays.sort( limits );
      splines = new Cubic[ x.length - 1 ];
      for( int i = 0; i < splines.length; i++ )
         {
	 /*double values[] = backSolve( new double[][] { 
	        { 1, limits[i], Math.pow( limits[i], 2.0 ), Math.pow( limits[i], 3.0 ), 
		  -1, -limits[i+1], -Math.pow( limits[i+1], 2.0 ), -Math.pow( limits[i+1], 3.0 ), 
		  y[i].doubleValue() - y[i+1].doubleValue() },

		{ 1, limits[i+1], Math.pow( limits[i+1], 2.0 ), Math.pow( limits[i+1], 3.0 ), 
		  -1, -limits[i+2], -Math.pow( limits[i+2], 2.0 ), -Math.pow( limits[i+2], 3.0 ), 
		  y[i+1].doubleValue() - y[i+2].doubleValue() },

	        { 1, limits[i], Math.pow( limits[i], 2.0 ), Math.pow( limits[i], 3.0 ), 
		  -1, -limits[i+1], -Math.pow( limits[i+1], 2.0 ), -Math.pow( limits[i+1], 3.0 ), 0 },

		{ 1, limits[i], Math.pow( limits[i], 2.0 ), Math.pow( limits[i], 3.0 ), 
		  -1, -limits[i+2], -Math.pow( limits[i+2], 2.0 ), -Math.pow( limits[i+2], 3.0 ), 
		  y[i].doubleValue() - y[i+2].doubleValue() },

		{ -3 / limits[i+1], -2, -limits[i+1], 0, 3 / limits[i+1], 2, limits[i+1], 0, 0 },

		{ -3 / Math.pow( limits[i], 2.0 ), -3 / limits[i], -2, 0, 3 / Math.pow( limits[i+1], 2.0 ), 3 / limits[i+1], 2, 0,
		  3 * y[i+1].doubleValue() / Math.pow( limits[i+1], 2.0 ) - 3 * y[i].doubleValue() / Math.pow( limits[i], 2.0 ) },

		{ -3 / Math.pow( limits[i], 2.0 ), -3 / limits[i], -2, 0, 3 / Math.pow( limits[i+2], 2.0 ), 3 / limits[i+2], 4, 0, 
		  3 * y[i+2].doubleValue() / Math.pow( limits[i+2], 2.0 ) - 3 * y[i].doubleValue() / Math.pow( limits[i], 2.0 ) },

		{ 1 / Math.pow( limits[i], 2.0 ), 1 / limits[i], 0, -2 * limits[i],
		  -1 / Math.pow( limits[i+2], 2.0 ), -1 / limits[i+2], 0, 2 * limits[i+2], 
		  y[i].doubleValue() / Math.pow( limits[i], 2.0 ) - y[i+2].doubleValue() / Math.pow( limits[i+2], 2.0 ) } } );*/

	/*System.out.println( "values: " );
	for( int j = 0; j < values.length; j++ )
	   System.out.println( values[j] + " " );*/
	Double cubic1[] = new Double[2];
	Double cubic2[] = new Double[2];
	System.arraycopy( x, i, cubic1, 0, 2 );
	System.arraycopy( y, i, cubic2, 0, 2 );
	splines[i] = new Cubic( cubic1, cubic2 );
	//splines[i+1] = new Cubic( cubic2 );
	}//end for
      }//end method

   public double getY( double x )
      {
      int i;
      for( i = 1; i < splines.length; i++ )
         {
	 //System.out.println( i + "\n" );
         if( x > limits[i] ) break;
	 }
      return splines[--i].getY( x );
      }

   public Cubic getSpline( int i )
      {
      return splines[i % splines.length];
      }

   public double[] getParams( int i )
      {
      return splines[i % splines.length].getParams();
      }

   public double[] getParams()
      {
      return getParams( 0 );
      }

   }
