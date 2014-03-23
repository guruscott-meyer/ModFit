import java.util.Arrays;

public class Splines extends Formula
   {

   private Linear[] splines;
   private double[] limits;

   public Splines( Double[] x, Double[] y )
      {
      super();
      limits = new double[ x.length ];
      for( int i = 0; i < x.length; i++ )
         limits[i] = x[i].doubleValue();
      //Arrays.sort( limits );
      splines = new Linear[ x.length - 1 ];
      for( int i = 0; i < splines.length; i++ )
         {
	Double lineX[] = new Double[2];
	Double lineY[] = new Double[2];
	System.arraycopy( x, i, lineX, 0, 2 );
	System.arraycopy( y, i, lineY, 0, 2 );
	splines[i] = new Linear( lineX, lineY );
	}//end for
      /*System.out.println( "limits: " );
      for( int j = 0; j < limits.length; j++ )
	 System.out.println( limits[j] + " " );*/
      }//end method

   public double getY( double x )
      {
      int i;
      for( i = 1; i < splines.length; i++ )
         {
	 //System.out.println( i + "\n" );
         if( x < limits[i] ) break;
	 }
      //System.out.println( x + ": " + i );
      return splines[--i].getY( x );
      }

   public Linear getSpline( int i )
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
