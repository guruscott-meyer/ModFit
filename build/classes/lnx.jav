public class LnX extends Formula
   {
   
   public LnX( Double[] x, Double[] y )
      {
      super( new double[] { powerFormA( log( x ), y ), powerFormB( log( x ), y ) } );
      }

   public double getY( double x )
      {
      if( x == 0 ) x = 0.000000001;
      return Params[0] * Math.log( x ) + Params[1];
      }

   }
