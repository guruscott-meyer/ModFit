public class X2 extends Formula
   {

   public X2( Double[] x, Double[] y )
      {
      super( new double[] { powerFormA2( x, y, 2.0 ) } );
      }

   public double getY( double x )
      {
      return Params[0] * Math.pow( x, 2.0 );
      }

   }
