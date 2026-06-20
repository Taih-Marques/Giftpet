import { Routes } from '@angular/router';
import { Home } from './home/home';
import { CampaignList } from './campaign/campaign-list/campaign-list';
import { EventForm } from './event/event-form/event-form';
import { EventDetailsComponent } from './event/event-details/event-details';
import { EventList } from './event/event-list/event-list';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full',
  },
  {
    path: 'home',
    component: Home,
  },
  {
    path: 'campaigns',
    component: CampaignList,
  },
  {
    path: 'new-event',
    component: EventForm,
  },
  {
    path: 'events',
    component: EventList,
  },
  {
    path: 'event/:id',
    component: EventDetailsComponent,
  },
  {
    path: '**',
    redirectTo: '/home',
  },
];
