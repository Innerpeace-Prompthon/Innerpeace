import type { TravelDay } from "../types/travelSchedule";
import api from "./api";

interface GetMessageArgumentType {
  userInput: string;
  date: string;
  region: string;
  travelType: string;
  transportation: string;
}

export const fetchTravelPlan = async (formData: GetMessageArgumentType) => {
  // const { data } = await api.post(`/api/lass/chat`, formData);

  return [
    {
      day: "Day 1",
      date: "2025-07-15",
      plan: [
        {
          order: 1,
          place: "경복궁",
          description:
            "조선 시대의 대표 궁궐로, 아름다운 건축물과 정원을 감상할 수 있는 고궁입니다.",
          activity: "고궁 산책 및 사진 촬영",
          image: "https://example.com/images/gyeongbokgung.jpg",
          latitude: 37.579617,
          longitude: 126.977041,
        },
        {
          order: 2,
          place: "광장시장",
          description:
            "서울의 대표적인 전통시장으로, 다양한 길거리 음식과 로컬 문화를 체험할 수 있습니다.",
          activity: "점심 식사 및 시장 구경",
          image: "https://example.com/images/gwangjang-market.jpg",
          latitude: 37.570377,
          longitude: 126.999213,
        },
        {
          order: 3,
          place: "북촌 한옥마을",
          description:
            "전통 한옥들이 모여 있는 조용한 마을로, 골목 산책과 감성적인 풍경을 즐길 수 있습니다.",
          activity: "조용한 골목 산책",
          image: "https://example.com/images/bukchon.jpg",
          latitude: 37.582604,
          longitude: 126.983998,
        },
      ],
    },
    {
      day: "Day 2",
      date: "2025-07-16",
      plan: [
        {
          order: 1,
          place: "한강공원",
          description:
            "서울을 가로지르는 한강변에 조성된 공원으로, 강바람을 맞으며 산책이나 자전거 타기를 즐길 수 있습니다.",
          activity: "자전거 타기 및 강가 산책",
          image: "https://example.com/images/hangang.jpg",
          latitude: 37.520637,
          longitude: 126.939564,
        },
        {
          order: 2,
          place: "망원시장",
          description:
            "현지인들이 즐겨 찾는 재래시장으로, 소박하고 정겨운 분위기에서 먹거리를 즐길 수 있습니다.",
          activity: "로컬 푸드 체험 및 점심",
          image: "https://example.com/images/mangwon-market.jpg",
          latitude: 37.556477,
          longitude: 126.910941,
        },
        {
          order: 3,
          place: "서울숲",
          description:
            "도심 속 자연을 느낄 수 있는 대형 공원으로, 조용한 분위기에서 여유로운 시간을 보내기 좋습니다.",
          activity: "조용한 산책 및 자연 감상",
          image: "https://example.com/images/seoulforest.jpg",
          latitude: 37.54456,
          longitude: 127.037801,
        },
      ],
    },
  ] as TravelDay[];
};
