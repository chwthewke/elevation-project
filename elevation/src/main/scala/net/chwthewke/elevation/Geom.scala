package net.chwthewke.elevation

import geotrellis.raster.Tile

case class Geom( cols: Int, rows: Int ) {

  val halfColsDouble: Double = cols.toDouble / 2d
  val rowsDouble: Double     = rows.toDouble

  final def northRadians( row: Int ): Double =
    (0.5d - row / rowsDouble) * math.Pi

  final def southRadians( row: Int ): Double =
    (0.5d - ((row + 1) / rowsDouble)) * math.Pi

  // Here "scaled" means divided by R²
  def scaledCellArea( row: Int ): Double =
    math.Pi / halfColsDouble * (math.sin( northRadians( row ) ) - math.sin( southRadians( row ) ))

  final def scaledVolumeWith( f: Int => Double ): ( Int, Int, Int ) => Double = {
    case ( col @ _, row, elev ) =>
      scaledCellArea( row ) * f( elev )
  }

  def scaledVolumeUnder( elevation: Double ): ( Int, Int, Int ) => Double =
    scaledVolumeWith( e => if (e < elevation) (elevation - e) else 0d )

  def scaledVolumeOver( elevation: Double ): ( Int, Int, Int ) => Double =
    scaledVolumeWith( e => if (e > elevation) (e - elevation) else 0d )

}

object Geom {
  val scaledArea: Double        = 4 * math.Pi
  def apply( tile: Tile ): Geom = Geom( tile.cols, tile.rows )
}
