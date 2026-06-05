import type { Page, Route } from '@playwright/test';

export const authToken = 'e2e-token';

const corsHeaders = {
  'Access-Control-Allow-Headers': 'Authorization, Content-Type',
  'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
  'Access-Control-Allow-Origin': '*',
};

type TopicFixture = {
  id: number;
  name: string;
  description: string;
  subscribed: boolean;
};

type PostFixture = {
  id: number;
  title: string;
  content: string;
  author: string;
  topic: string;
  createdAt: string;
};

type UserFixture = {
  id: number;
  username: string;
  email: string;
  subscriptions: TopicFixture[];
};

type LoginPayload = {
  emailOrUsername: string;
  password: string;
};

type RegisterPayload = {
  username: string;
  email: string;
  password: string;
};

type CreatePostPayload = {
  topicId: number;
  title: string;
  content: string;
};

type UpdateUserPayload = {
  username: string;
  email: string;
  password?: string;
};

export type MockApi = {
  loginRequests: LoginPayload[];
  registerRequests: RegisterPayload[];
  createPostRequests: CreatePostPayload[];
  updateUserRequests: UpdateUserPayload[];
  subscribeRequests: number[];
  unsubscribeRequests: number[];
};

export async function mockApi(page: Page): Promise<MockApi> {
  const api: MockApi = {
    loginRequests: [],
    registerRequests: [],
    createPostRequests: [],
    updateUserRequests: [],
    subscribeRequests: [],
    unsubscribeRequests: [],
  };

  const topics: TopicFixture[] = [
    {
      id: 1,
      name: 'Angular',
      description: 'Architecture, composants standalone et formulaires.',
      subscribed: false,
    },
    {
      id: 2,
      name: 'RxJS',
      description: 'Observables, streams et composition reactive.',
      subscribed: true,
    },
  ];

  const posts: PostFixture[] = [
    {
      id: 1,
      title: 'Bienvenue sur MDD',
      content: 'Un premier article pour suivre les sujets de developpement.',
      author: 'Alice',
      topic: 'Angular',
      createdAt: '2026-05-20T10:00:00.000Z',
    },
  ];

  let user: UserFixture = {
    id: 7,
    username: 'marie',
    email: 'marie@example.com',
    subscriptions: [clone(topics[1])],
  };

  await page.route('**/api/**', async (route) => {
    const request = route.request();
    const method = request.method();
    const url = new URL(request.url());
    const path = url.pathname.replace(/^\/api/, '');

    if (method === 'OPTIONS') {
      await route.fulfill({
        status: 204,
        headers: corsHeaders,
      });
      return;
    }

    if (method === 'POST' && path === '/auth/register') {
      api.registerRequests.push(request.postDataJSON() as RegisterPayload);
      await fulfillJson(route, { token: authToken });
      return;
    }

    if (method === 'POST' && path === '/auth/login') {
      api.loginRequests.push(request.postDataJSON() as LoginPayload);
      await fulfillJson(route, { token: authToken });
      return;
    }

    if (method === 'GET' && path === '/posts/feed') {
      await fulfillJson(route, posts);
      return;
    }

    if (method === 'POST' && path === '/posts') {
      const payload = request.postDataJSON() as CreatePostPayload;
      const topic = topics.find((item) => item.id === payload.topicId);

      api.createPostRequests.push(payload);
      posts.unshift({
        id: 99,
        title: payload.title,
        content: payload.content,
        author: 'Marie',
        topic: topic?.name ?? 'Inconnu',
        createdAt: '2026-05-21T12:00:00.000Z',
      });

      await fulfillJson(route, { id: 99 });
      return;
    }

    if (method === 'GET' && path === '/topics') {
      await fulfillJson(route, topics);
      return;
    }

    const subscriptionMatch = path.match(/^\/topics\/(\d+)\/subscribe$/);

    if (subscriptionMatch && method === 'POST') {
      const topicId = Number(subscriptionMatch[1]);
      updateTopic(topics, topicId, true);
      api.subscribeRequests.push(topicId);
      await fulfillJson(route, {});
      return;
    }

    if (subscriptionMatch && method === 'DELETE') {
      const topicId = Number(subscriptionMatch[1]);
      updateTopic(topics, topicId, false);
      user = {
        ...user,
        subscriptions: user.subscriptions.filter((topic) => topic.id !== topicId),
      };
      api.unsubscribeRequests.push(topicId);
      await fulfillJson(route, {});
      return;
    }

    if (method === 'GET' && path === '/users/me') {
      await fulfillJson(route, user);
      return;
    }

    if (method === 'PUT' && path === '/users/me') {
      const payload = request.postDataJSON() as UpdateUserPayload;

      api.updateUserRequests.push(payload);
      user = {
        ...user,
        username: payload.username,
        email: payload.email,
      };

      await fulfillJson(route, user);
      return;
    }

    await fulfillJson(route, { message: `No e2e mock for ${method} ${path}` }, 404);
  });

  return api;
}

function updateTopic(topics: TopicFixture[], topicId: number, subscribed: boolean): void {
  const topic = topics.find((item) => item.id === topicId);

  if (topic) {
    topic.subscribed = subscribed;
  }
}

function clone<T>(value: T): T {
  return JSON.parse(JSON.stringify(value)) as T;
}

async function fulfillJson(route: Route, body: unknown, status = 200): Promise<void> {
  await route.fulfill({
    status,
    contentType: 'application/json',
    headers: corsHeaders,
    body: JSON.stringify(body),
  });
}
