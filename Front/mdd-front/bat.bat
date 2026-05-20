@echo off
title MDD Angular Structure Setup

echo ==========================================
echo Creation architecture Angular MDD
echo ==========================================

:: CORE
mkdir src\app\core
mkdir src\app\core\guards
mkdir src\app\core\interceptors
mkdir src\app\core\models
mkdir src\app\core\services
mkdir src\app\core\utils

:: FEATURES
mkdir src\app\features
mkdir src\app\features\auth
mkdir src\app\features\auth\login
mkdir src\app\features\auth\register

mkdir src\app\features\profile
mkdir src\app\features\profile\profile

mkdir src\app\features\topics
mkdir src\app\features\topics\topics-list

mkdir src\app\features\posts
mkdir src\app\features\posts\feed
mkdir src\app\features\posts\post-detail
mkdir src\app\features\posts\create-post

mkdir src\app\features\comments

:: SHARED
mkdir src\app\shared
mkdir src\app\shared\components
mkdir src\app\shared\pipes
mkdir src\app\shared\directives

:: LAYOUTS
mkdir src\app\layouts
mkdir src\app\layouts\navbar
mkdir src\app\layouts\footer

echo ==========================================
echo Generation composants Angular
echo ==========================================

:: AUTH
call ng g module features/auth --routing

call ng g component features/auth/login
call ng g component features/auth/register

:: PROFILE
call ng g component features/profile/profile

:: TOPICS
call ng g component features/topics/topics-list

:: POSTS
call ng g component features/posts/feed
call ng g component features/posts/post-detail
call ng g component features/posts/create-post

:: LAYOUTS
call ng g component layouts/navbar
call ng g component layouts/footer

echo ==========================================
echo Generation services
echo ==========================================

call ng g service core/services/auth
call ng g service core/services/user
call ng g service core/services/topic
call ng g service core/services/post
call ng g service core/services/comment

echo ==========================================
echo Generation guard JWT
echo ==========================================

call ng g guard core/guards/auth --skip-tests

echo ==========================================
echo Generation interceptor JWT
echo ==========================================

call ng g interceptor core/interceptors/jwt

echo ==========================================
echo Creation interfaces
echo ==========================================

type nul > src\app\core\models\user.interface.ts
type nul > src\app\core\models\topic.interface.ts
type nul > src\app\core\models\post.interface.ts
type nul > src\app\core\models\comment.interface.ts
type nul > src\app\core\models\auth.interface.ts

echo ==========================================
echo Configuration environment.ts
echo ==========================================

(
echo export const environment = {
echo   production: false,
echo   apiUrl: 'http://localhost:8080/api'
echo };
) > src\environments\environment.ts

echo ==========================================
echo Structure Angular MDD cree avec succes !
echo ==========================================

pause