public class PowerOfE extends Formula
   {

   public PowerOfE( Double[] x, Double[] y )
      {
      super( new double[] { powerFormA( x, log( y ) ), Math.exp( powerFormB( x, log( y ) ) ) }  );
      }

   public double getY( double x )
      {
      return Params[1] * Math.exp( Params[0] * x );
      }

   }
