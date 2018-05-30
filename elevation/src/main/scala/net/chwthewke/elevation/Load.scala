package net.chwthewke.elevation

import geotrellis.raster._
import geotrellis.raster.io.geotiff._
import geotrellis.raster.io.geotiff.reader.GeoTiffReader
import geotrellis.raster.resample.Average

object Load {
  def bedrock: SinglebandGeoTiff =
    GeoTiffReader
      .readSingleband( "C:\\Users\\chwth\\Downloads\\ETOPO1_Bed_c_geotiff.tif" )
  def icesheet: SinglebandGeoTiff =
    GeoTiffReader
      .readSingleband( "C:\\Users\\chwth\\Downloads\\ETOPO1_Ice_c_geotiff.tif" )

  def downsample( gt: SinglebandGeoTiff ) =
    gt.mapTile( t => t.resample( t.cols / 10, t.rows / 10, Average ) )

  def bedrockDown: SinglebandGeoTiff  = downsample( bedrock )
  def icesheetDown: SinglebandGeoTiff = downsample( icesheet )

}
