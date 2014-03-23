public class PowerOfX extends Formula
   {
   
   public PowerOfX( Double[] x, Double[] y )
      {
      super( new double[] { powerFormA( log( x ), log( y ) ), Math.exp( powerFormB( log( x ), log( y ) ) ) } );
      }


   public double getY( double x )
      {
      return Params[1] * Math.pow( x, Params[0] );
      }

   }
