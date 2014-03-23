public abstract class Formula extends Object
   {

   protected double[] Params;

   public Formula()
      {
      }

   public Formula( double[] params )
      {
      Params = params;
      }

   public double[] getParams()
      {
      return Params;
      }

   public abstract double getY( double x );

   public static double sigma( Double[] list )
      {
      double temp = 0;
      for( int i = 0; i < list.length; i++ )
         temp += list[i].doubleValue();
      return temp;
      }

   public static double sigma( Double[] list, double exp )
      {
      double temp = 0;
      for( int i = 0; i < list.length; i++ )
         temp += Math.pow( list[i].doubleValue(), exp );
      return temp;
      }

   public static double sigma( Double[] listx, Double[] listy )
      {
      double temp = 0;
      for( int i = 0; i < listx.length; i++ )
         temp += listx[i].doubleValue() * listy[i].doubleValue();
      return temp;
      }

   public static double sigma( Double[] listx, Double[] listy, double exp )
      {
      double temp = 0;
      for( int i = 0; i < listx.length; i++ )
         temp += Math.pow( listx[i].doubleValue(), exp ) * listy[i].doubleValue();
      return temp;
      }

   public static double powerFormA( Double[] x, Double[] y )
      {
      return ( x.length * sigma( x, y) - sigma( x ) * sigma( y ) ) /
             ( x.length * sigma( x, 2.0 ) - Math.pow( sigma( x ), 2.0 ) );
      }

   public static double powerFormB( Double[] x, Double[] y )
      {
      return ( sigma( x, 2.0 ) * sigma( y ) - sigma( x, y ) * sigma( x ) ) /
             ( x.length * sigma( x, 2.0 ) - Math.pow( sigma( x ), 2.0 ) );
      }

   public static double powerFormA2( Double[] x, Double[] y, double exp )
      {
      return sigma( x, y, exp ) / sigma( x, exp * 2.0 );
      }

   public static double[] backSolve( double system[][] )
      {
      int i = 0, j = 0, k = 0;
      double multiplier = 0;
      for( i = 0; i < system.length - 1; i++ )
         for( j = i + 1; j < system.length; j++ )
            {
            if( system[j][i] != 0 ) 
               {
               multiplier = system[j][i] / system[i][i];
               for( k = i; k < system[j].length; k++ )
                  system[j][k] -= system[i][k] * multiplier;
               }
            }
      double result[] = new double[ system.length ];
      for( i = system.length - 1; i >= 0; i-- )
         {
         for( j = i + 1; j < system[i].length - 1; j++ )
            system[i][ system[i].length - 1 ] -= system[i][j] * result[j];
         if( system[i][i] != 0 ) result[i] = system[i][ system[i].length - 1 ] / system[i][i];
         else result[i] = 0;
         }
      return result;
      }

   public static Double[] log( Double[] x )
      {
      Double[] y = new Double[ x.length ];
      for( int i = 0; i < x.length; i++ )
         y[i] = new Double( Math.log( x[i].doubleValue() ) );
      return y;
      }

   }
