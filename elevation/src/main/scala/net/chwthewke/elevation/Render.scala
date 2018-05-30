package net.chwthewke.elevation

import geotrellis.raster._
import geotrellis.raster.io.geotiff.SinglebandGeoTiff
import geotrellis.raster.render.ColorMap
import geotrellis.raster.render.ColorRamps

object Render {
  val normalColorMap = {
    val stops =
      (0 to 4).map( i => (-8000 + 2000 * i).toDouble   -> ColorRamps.BlueToOrange.colors( i ) ) ++
        (0 to 5).map( i => (500 + 500 * i).toDouble    -> ColorRamps.GreenToRedOrange.colors( i ) ) ++
        (6 to 9).map( i => (-4500 + 1500 * i).toDouble -> ColorRamps.GreenToRedOrange.colors( i ) )
    ColorMap( stops.toMap )
  }

  val invertedColorMap = {
    val stops =
      (0 to 4).map( i => (-8000 + 2000 * i).toDouble   -> ColorRamps.BlueToOrange.colors( i ) ) ++
        (0 to 5).map( i => (500 + 500 * i).toDouble    -> ColorRamps.GreenToRedOrange.colors( i ) ) ++
        (6 to 9).map( i => (-2000 + 1000 * i).toDouble -> ColorRamps.GreenToRedOrange.colors( i ) )
    ColorMap( stops.toMap )
  }

  def savePng( gt: SinglebandGeoTiff, cm: ColorMap, path: String ) = {
    gt.tile.renderPng( cm ).write( path )
  }

}
