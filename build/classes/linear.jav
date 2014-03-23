public class Linear extends Formula
   {

   public Linear( Double[] x, Double[] y )
      {
      super( new double[] { powerFormA( x, y ), powerFormB( x, y ) } );
      }

   public double getY( double x )
      {
      return Params[0] * x + Params[1];
      }

   }
