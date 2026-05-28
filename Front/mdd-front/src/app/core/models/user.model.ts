import { Topic }
from './topic.model';

export interface User {

  id: number;

  username: string;

  email: string;

  subscriptions: Topic[];
}