import React, { useState } from "react";
import type { TravelSpot } from "../../types/travelSchedule";
import { Polyline, CustomOverlayMap } from "react-kakao-maps-sdk";
import * as S from "../../styles/KakaoMap.style";

interface PolylineAndMarkersPropsType {
  places: TravelSpot[];
  color: string;
  keyPrefix: string;
}

const PolylineAndMarkers: React.FC<PolylineAndMarkersPropsType> = ({
  places,
  color,
  keyPrefix,
}) => {
  const [hoveredPlace, setHoveredPlace] = useState<TravelSpot | null>(null);

  const validPoints = places
    .filter(
      ({ longitude, latitude }) =>
        !isNaN(longitude ?? 0) && !isNaN(latitude ?? 0)
    )
    .map(({ longitude, latitude }) => ({
      lng: longitude ?? 0,
      lat: latitude ?? 0,
    }));

  return (
    <React.Fragment key={keyPrefix}>
      <Polyline
        path={validPoints}
        strokeWeight={5}
        strokeOpacity={1}
        strokeColor={color}
        strokeStyle="dashed"
      />

      {places.map((item) => {
        const {
          order,
          longitude,
          latitude,
          place,
          description,
          activity,
          image,
        } = item;

        if (isNaN(longitude ?? 0) || isNaN(latitude ?? 0)) return null;

        const position = { lng: longitude!, lat: latitude! };
        const key = `${keyPrefix}-${order}-${place}-${longitude}-${latitude}`;

        return (
          <React.Fragment key={key}>
            <CustomOverlayMap position={position} yAnchor={0.5}>
              <S.CustomMarker
                $color={color}
                onMouseEnter={() => setHoveredPlace(item)}
                onMouseLeave={() => setHoveredPlace(null)}
              >
                {order}
              </S.CustomMarker>
            </CustomOverlayMap>

            {hoveredPlace?.order === order && hoveredPlace.place === place && (
              <CustomOverlayMap position={position} yAnchor={1}>
                <S.MarkerTooltip>
                  <img src={image ?? ""} alt={`${place} 이미지`} />
                  <div>
                    {order}. {place}
                  </div>
                  <div>{activity}</div>
                  <div>{description}</div>
                </S.MarkerTooltip>
              </CustomOverlayMap>
            )}
          </React.Fragment>
        );
      })}
    </React.Fragment>
  );
};

export default PolylineAndMarkers;
