import { Comment }
from './comment.model';

export interface PostDetail {

  id: number;

  title: string;

  content: string;

  author: string;

  topic: string;

  createdAt: string;

  comments: Comment[];
}