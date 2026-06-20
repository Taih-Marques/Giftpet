export interface Event {
  id: number;
  name: string;
  description: string;
  campaign: { id: number; name: string };
  goal: number;
  date: Date;
  images: { id: string }[];
  totalCards: number;
  claimedCards: number;
  amountRaised: number;
  giftCards: { code: string; suggestedAmount: number; dateUsed: Date | null }[];
}
