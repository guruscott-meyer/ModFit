public class X3 extends Formula
   {

   public X3( Double[] x, Double[] y )
      {
      super( new double[] { powerFormA2( x, y, 3.0 ) } );
      }

   public double getY( double x )
      {
      return Params[0] * Math.pow( x, 3.0 );
      }

   }
