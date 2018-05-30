package net.chwthewke.elevation

import geotrellis.raster.io.geotiff.SinglebandGeoTiff

object scripts {
  def standardElevationMap(): Unit = {
    val data = Load.bedrockDown
    Render.savePng( data, Render.normalColorMap, "render_std.png" )
  }

  def invertedSameWaterVolumeMap(): Unit = {
    val data = Load.bedrockDown.mapTile( _.map( -4998 - _ ) )
    Render.savePng( data, Render.invertedColorMap, "render_inv.png" )
  }

  // "scaled" voume means divided by a 4*pi*R² factor

  def scaledVolumeUnderZero( data: SinglebandGeoTiff = Load.bedrockDown ): Double = {
    val g = Geom( data.tile )
    data.tile.foldAt( 0d )( ( acc, c ) => acc + g.scaledVolumeUnder( 0d ).tupled( c ) ) / Geom.scaledArea
  }

  def scaledVolumeOver( elev: Double, data: SinglebandGeoTiff = Load.bedrockDown ): Double = {
    val g = Geom( data.tile )

    data.tile.foldAt( 0d )( ( acc, c ) => acc + g.scaledVolumeOver( elev ).tupled( c ) ) / Geom.scaledArea
  }

  def landMassOver( elev: Double, data: SinglebandGeoTiff = Load.bedrockDown ): Double = {
    val g = Geom( data.tile )

    def scaledAreaOver( col: Int, row: Int, value: Int ): Double =
      if (value > elev) g.scaledCellArea( row ) else 0d

    data.tile.foldAt( 0d )( ( acc, c ) => acc + (scaledAreaOver _).tupled( c ) ) / Geom.scaledArea
  }

}
