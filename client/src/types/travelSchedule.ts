export interface TravelSpot {
  order: number;
  place: string;
  description: string;
  activity: string;
  image: string | null;
  latitude: number | null;
  longitude: number | null;
}

export interface TravelDay {
  day: string;
  date: string;
  plan: TravelSpot[];
}

export interface TravelScheduleStoreType {
  travelSchedule: TravelDay[];
  colors: string[];

  addTravelSchedule: (travelSchedule: TravelDay[]) => void;
  deleteTravelSchedule: () => void;
  changeColor: (color: string, index: number) => void;
}
