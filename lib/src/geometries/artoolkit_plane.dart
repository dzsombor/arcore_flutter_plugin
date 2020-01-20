import 'package:arcore_flutter_plugin/src/geometries/artoolkit_geometry.dart';
import 'package:arcore_flutter_plugin/src/geometries/artoolkit_material.dart';
import 'package:flutter/widgets.dart';

/// Represents a rectangle with controllable width and height. The plane has one visible side.
class ARToolKitPlane extends ARToolKitGeometry {
  ARToolKitPlane({
    double width = 1,
    double height = 1,
    this.widthSegmentCount = 1,
    this.heightSegmentCount = 1,
    List<ARToolKitMaterial> materials,
  })  : width = ValueNotifier(width),
        height = ValueNotifier(height),
        super(
          materials: materials,
        );

  /// The plane extent along the X axis.
  /// If the value is less than or equal to 0, the geometry is empty.
  /// The default value is 1.
  final ValueNotifier<double> width;

  /// The plane extent along the Y axis.
  /// If the value is less than or equal to 0, the geometry is empty.
  /// The default value is 1.
  final ValueNotifier<double> height;

  /// The number of subdivisions along the X axis.
  /// If the value is less than 1, the behavior is undefined.
  /// The default value is 1.
  final int widthSegmentCount;

  /// The number of subdivisions along the Y axis. The default value is 1.
  /// If the value is less than 1, the behavior is undefined.
  /// The default value is 1.
  final int heightSegmentCount;

  @override
  Map<String, dynamic> toMap() => <String, dynamic>{
        'width': width.value,
        'height': height.value,
        'widthSegmentCount': widthSegmentCount,
        'heightSegmentCount': heightSegmentCount,
      }..addAll(super.toMap());
}
