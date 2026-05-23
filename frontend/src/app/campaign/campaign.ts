export interface Campaign {
  id: number;
  name: string;
  description: string;
  goal: number;
  date: Date;
  images: [{ id: string }]
}
